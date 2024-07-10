package com.DataSense.DataSenseApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/datasense/api/v1/equipments")
public class DataSenseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSenseApiApplication.class, args);
	}

}
