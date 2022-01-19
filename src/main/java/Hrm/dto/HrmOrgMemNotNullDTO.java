package Hrm.dto;

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
public class HrmOrgMemNotNullDTO {
	private Integer id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private List<HrmOrgMemNotNullDTO> children;
	
	public HrmOrgMemNotNullDTO(final HrmOrgSchDTO hrmOrgSchDTO) {
		this.id = hrmOrgSchDTO.getId();
		this.type = hrmOrgSchDTO.getType();
		this.name = hrmOrgSchDTO.getName();
		this.code = hrmOrgSchDTO.getCode();
		this.manager = hrmOrgSchDTO.getManager();
		if(!(hrmOrgSchDTO.getChildren()==null)) {
			this.children = hrmOrgSchDTO.getChildren().stream().map(HrmOrgMemNotNullDTO::new).filter(h ->!(h.getType()==null)).collect(Collectors.toList());
			if(this.children.isEmpty()) {
				this.children = null;
			}
		}
	}
}
