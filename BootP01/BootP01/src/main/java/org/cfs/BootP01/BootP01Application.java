package org.cfs.BootP01;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootP01Application implements CommandLineRunner {

	private final NotificationService notificationService;

	public BootP01Application(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public static void main(String[] args)
	{
		SpringApplication.run(BootP01Application.class, args);
	}

	public void run(String... args)
	{
		notificationService.pushService();
	}

}
