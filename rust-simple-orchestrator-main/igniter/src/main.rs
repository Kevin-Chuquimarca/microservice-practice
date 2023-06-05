use lazy_static::lazy_static;
use serde::{Deserialize, Serialize};
use std::error::Error;
use std::fs::File;
use std::io::prelude::*;
use std::io::{ Error as IoError, ErrorKind};
use std::process::{Command, Stdio};
use std::thread;
use std::time::Duration;

#[macro_use]
extern crate rocket;

#[derive(Debug, Deserialize, Serialize)]
struct Config {
    start_command: String,
    path: String,
}

lazy_static! {
    static ref CONFIG: Config = read_config("config.yaml");
}

/** Reads YAML config file from a given path*/
fn read_config(path: &str) -> Config {
    let mut file = File::open(path).expect("Failed to open file");
    let mut contents = String::new();
    file.read_to_string(&mut contents)
        .expect("Failed to read file");
    let config_result: Result<Config, serde_yaml::Error> = serde_yaml::from_str(&contents);
    let config: Config = match config_result {
        Ok(config) => config,
        Err(err) => panic!("Failed to parse YAML: {}", err),
    };

    config
}

/** Starts the server given a port, the command and path of a given application */
fn start_server( port: i32) -> Result<(), Box<dyn Error>> {
    let mut cmd = Command::new("cmd");
    cmd.arg("/C") // run the command in a shell and close the shell when done
        .arg(format!("cd {}", CONFIG.path)) // change directory to the specified path
        .arg(format!("& {} {}", CONFIG.start_command, port))
        .stdout(Stdio::piped()) // capture the stdout of the program
        .stderr(Stdio::piped()); // capture the stderr of the program

    // Start the command and get a handle to its standard output and error streams
    let mut child = cmd.spawn().expect("failed to start myprogram.exe");


    thread::sleep(Duration::from_millis(1000));

    match child.try_wait() {
        Ok(Some(status)) => {
            return Err(Box::new(IoError::new(
                ErrorKind::Other,
                format!("process exited with status code: {}", status),
            )));
        }
        Ok(_) => {
            println!("SERVER SUCCESFULLY STARTED AT PORT {}", port);
            return Ok(());
        }
        Err(e) => {
            return Err(Box::new(IoError::new(ErrorKind::Other, format!("{}", e))));
        }
    }
}

/**  Gives the order to start the server in a given port and checks if succesful*/
fn run_server(port: i32) -> String {
    match start_server(port) {
        Ok(()) => {
            println!("STARTED");
            "Server started successfully".to_owned()
    },
        Err(e) => format!("Failed to start server: {}", e),
    }
}

#[get("/<port>")]
fn index(port: i32) -> String {
    run_server(port)
}


#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![index])
}
