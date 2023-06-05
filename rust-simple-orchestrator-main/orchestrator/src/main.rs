use config::config::Server;
use lazy_static::lazy_static;
use reqwest::ClientBuilder;
use rocket::response::content;
use serde_json::Value;
use std::collections::HashMap;
use std::sync::Mutex;
use std::time::Duration;
mod config;
use crate::config::config::Config;

#[macro_use]
extern crate rocket;

/* Global variables */
lazy_static! {
    static ref CONFIG: Config = Config::new("config.yaml");
}

// This is a mutable global variable
lazy_static! {
    static ref PORTS: Mutex<HashMap<String, HashMap<i32, i32>>> = Mutex::new(get_ports());
}

/** Makes an HTTP request in the pattern http://address:port/uri/.../q */
async fn make_request(
    port: i32,
    address: &str,
    q: &str,
    uri: &str,
) -> Result<Value, Box<dyn std::error::Error>> {
    let request_url = format!("http://{}:{}{}/{}", address, port, uri, q);
    let timeout = Duration::new(1, 0);
    let client = ClientBuilder::new().timeout(timeout).build()?;
    let response = client.get(&request_url).send().await?;

    if response.status().is_success() {
        let body = response.text().await?;
        let json: Value = serde_json::from_str(&body)?;
        Ok(json)
    } else {
        Ok(Value::Null)
    }
}

/** Makes a request and adds context to the company from which it came from */
async fn make_request_data(
    port: i32,
    address: &str,
    q: &str,
    uri: &str,
    company: &str,
) -> content::RawJson<String> {
    let res = make_request(port, address, q, uri).await;

    let json_str = match res {
        Ok(json) => json.to_string(),
        Err(err) => {
            eprintln!("Error: {}", err);
            Value::Null.to_string()
        }
    };
    content::RawJson(String::from(format!(
        "{{\"company\": \"{}\", \"data\": {} }}",
        company, json_str
    )))
}

fn print_ports() {
    let data_lock = PORTS.lock().unwrap();

    for (address, ports) in data_lock.iter() {
        println!("Address: {}", address);
        for (port, usage) in ports.iter() {
            println!("Port: {}, Usage: {}", port, usage);
        }
        println!("------------------");
    }
}

fn find_smallest_port(server: &Server) -> Option<i32> {
    let smallest_port;
    let smallest_value;

    let mut data_lock = PORTS.lock().unwrap();
    let ports_usage = data_lock.get(&server.address).unwrap();
    smallest_port = ports_usage
        .iter()
        .min_by_key(|(_, used)| *used)
        .map(|(port, _)| *port);
    smallest_value = smallest_port.and_then(|port| ports_usage.get(&port).cloned());

    if let Some(port) = smallest_port {
        if let Some(value) = smallest_value {
            *data_lock
                .get_mut(&server.address)
                .unwrap()
                .get_mut(&port)
                .unwrap() = value + 1;
        }
    }

    smallest_port
}

#[get("/<q>")]
async fn index(q: String) -> content::RawJson<String> {
    let mut data_recovered = Vec::new();

    for server in &CONFIG.servers {
        let port;

        // Needed because async function
        {
            port = find_smallest_port(server)
                .expect(format!("No port found for {}", &server.address).as_str());
        }

        let res = make_request_data(port, &server.address, &q, &server.path, &server.company).await;

        let content::RawJson(json_string) = res;
        // Check if the response indicates an error
        let json_value: Value = serde_json::from_str(&json_string).unwrap_or_else(|_| Value::Null);
        if *json_value.get("data").unwrap() == Value::Null {
            let _ping = make_request_data(
                server.ignite_port,
                &server.address,
                &format!("{}", port),
                "",
                &server.company,
            )
            .await;
            data_recovered.push(
                make_request_data(port, &server.address, &q, &server.path, &server.company).await,
            )
        } else {
            data_recovered.push(content::RawJson(json_string));
        }
    }

    let values: Vec<Value> = data_recovered
        .into_iter()
        .map(|content::RawJson(json_string)| serde_json::from_str(&json_string))
        .collect::<Result<Vec<Value>, _>>()
        .expect("Failed to parse JSON");
    let json_string = serde_json::to_string(&values).expect("Failed to serialize JSON");

    print_ports();

    content::RawJson(String::from(format!("{{\"responses\": {}}}", json_string)))
}

/**
 * Gives ports in the following format
 *
 * ports = {
 *  "address1": {
 *  20: 0,
 *  21: 0,
 *  22: 0,
 * },
 *  "address2": {
 *  50: 0,
 *  51: 0,
 *  52: 0,
 * },
 * }
 *
 */
fn get_ports() -> HashMap<String, HashMap<i32, i32>> {
    let config = Config::new("config.yaml");

    let ports: HashMap<String, HashMap<i32, i32>> = config
        .servers
        .into_iter()
        .map(|ele| {
            let server_ports: HashMap<i32, i32> =
                ele.ports.into_iter().map(|port| (port, 0)).collect();
            (ele.address, server_ports)
        })
        .collect();

    ports
}

#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![index])
}
