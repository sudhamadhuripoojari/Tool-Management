package se.jfs.test.toolmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jfs.test.toolmanagementapi.exception.ResourceNotFoundException;
import se.jfs.test.toolmanagementapi.model.Tool;
import se.jfs.test.toolmanagementapi.repository.ToolRepository;

import javax.persistence.Column;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class ToolController {
    @Autowired
    private ToolRepository toolRepository;
   @GetMapping("/tools")
   public List<Tool> getAllTools(){
        return toolRepository.findAll();
    }

    @PostMapping("/tools")
    public Tool createTool(@RequestBody Tool tool){
       return toolRepository.save(tool);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<HttpStatus> deleteTool(@PathVariable long id){
//       Tool tool = toolRepository.findById(id)
//               .orElseThrow(()-> new ResourceNotFoundException("Tool doesn't exist with id" +id));
//       toolRepository.delete(tool);
//       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//    }
}
