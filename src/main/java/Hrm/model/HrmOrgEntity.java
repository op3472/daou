package Hrm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SequenceGenerator(
        name="USER_SEQ_GEN", //������ ���ʷ����� �̸�
        sequenceName="USER_SEQ", //������ �̸�
        initialValue=1, //���۰�
        allocationSize=1 //�޸𸮸� ���� �Ҵ��� ���� ������
        )
@Table(name ="HRM_ORG")
public class HrmOrgEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="USER_SEQ_GEN")
	private int id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="parent_id")
	private HrmOrgEntity parent;
	
	@OneToMany(mappedBy = "parent")
	private List<HrmOrgEntity> children = new ArrayList<>();
}
