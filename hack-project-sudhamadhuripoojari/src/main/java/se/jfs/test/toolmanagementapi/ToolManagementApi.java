package se.jfs.test.toolmanagementapi;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ToolManagementApi implements CommandLineRunner {
    public static void main(String... args) {
        SpringApplication.run(ToolManagementApi.class);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
