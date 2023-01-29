package com.janas.blog.report;

import com.janas.blog.enums.ReportReasonType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity @Data @Table(name = "report_reason")
public class ReportReason {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private ReportReasonType type;
    @Column(name = "name")
    private String name;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "reason")
    private Set<Report> reports;
}
