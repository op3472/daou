package Hrm.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class HrmOrgNotNullDTO {
	private Integer id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private List<HrmOrgNotNullDTO> children;
	
	public HrmOrgNotNullDTO(final HrmOrgDeptOnlySchDTO hrmOrgDeptOnlySchDTO) {
		this.id = hrmOrgDeptOnlySchDTO.getId();
		this.type = hrmOrgDeptOnlySchDTO.getType();
		this.name = hrmOrgDeptOnlySchDTO.getName();
		this.code = hrmOrgDeptOnlySchDTO.getCode();
		this.manager = hrmOrgDeptOnlySchDTO.getManager();
		if(!(hrmOrgDeptOnlySchDTO.getChildren()==null)) {
			this.children = hrmOrgDeptOnlySchDTO.getChildren().stream().map(HrmOrgNotNullDTO::new).filter(h ->!(h.getType()==null)&& !h.getType().equals("Member")).collect(Collectors.toList());
			if(this.children.isEmpty()) {
				this.children = null;
			}
		}
		
	}
	
}
