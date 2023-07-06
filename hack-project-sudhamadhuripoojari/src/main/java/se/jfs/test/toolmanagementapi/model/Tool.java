package se.jfs.test.toolmanagementapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="tools")
public class Tool {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "tool_name")
    private String toolName;

    @Column(name = "tool_owner")
    private  String toolOwner;

    @Column(name = "email")
    private String email;
}
