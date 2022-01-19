package Hrm.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import Hrm.model.HrmOrgEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class HrmOrgDeptOnlyDTO {
	private int id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private List<HrmOrgDeptOnlyDTO> children;
	
	public HrmOrgDeptOnlyDTO(final HrmOrgEntity entity) {

		this.id = entity.getId();
		this.type = entity.getType();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.manager = entity.getManager();
		if(!entity.getChildren().isEmpty()) {
			this.children = entity.getChildren().stream().map(HrmOrgDeptOnlyDTO::new).filter(h ->!h.getType().equals("Member")).collect(Collectors.toList());
			if(this.children.isEmpty()) {
				this.children = null;
			}
		}
		
	}
}
