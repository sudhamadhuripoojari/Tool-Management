package se.jfs.test.toolmanagementapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jfs.test.toolmanagementapi.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {

}
