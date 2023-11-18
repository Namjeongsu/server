package all.clear.domain.requirement;

import all.clear.domain.requirement.Requirement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RequirementComponent {
    @Id @GeneratedValue
    @Column(name = "requirement_component_id")
    private Long requirementComponentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @Column(name = "requirement_category")
    private String requirementCategory; //이수구분

    @Column(name = "requirement_argument")
    private String requirementArgument; //졸업요건

    @Column(name = "requirement_criteria")
    private Double requirementCriteria; //기준값
    @Column(name = "requirement_complete")
    private Double requirementComplete; //계산값

    @Column(name = "requirement_result")
    private String requirementResult; //중족여부

}