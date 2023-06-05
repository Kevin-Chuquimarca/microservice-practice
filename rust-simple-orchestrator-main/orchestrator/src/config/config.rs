use serde::{Deserialize, Serialize};
use std::fs::File;
use std::io::prelude::*;

#[derive(Debug, Deserialize, Serialize)]
pub struct Server {
    pub address: String,
    pub ports: Vec<i32>,
    pub ignite_port: i32,
    pub path: String,
    pub company: String,
}

#[derive(Debug, Deserialize, Serialize)]
pub struct Config {
    pub check_time: i32,
    pub servers: Vec<Server>,
}

impl Config {
    pub fn new(path: &str) -> Config {
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
}
