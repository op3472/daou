package Hrm.dto;

import java.util.ArrayList;
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
@JsonInclude(Include.NON_EMPTY)
public class HrmOrgDeptOnlySchDTO {
	private Integer id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private List<HrmOrgDeptOnlySchDTO> children;
	
	public HrmOrgDeptOnlySchDTO(final HrmOrgEntity entity,String key) {
		this.id = entity.getId();
		this.type = entity.getType();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.manager = entity.getManager();
		if(!entity.getChildren().isEmpty()) {
			this.children = new ArrayList<HrmOrgDeptOnlySchDTO>();
			
			for(int i=0; i<entity.getChildren().size(); i++) {
				if(entity.getChildren().get(i).getName().contains(key) && entity.getChildren().get(i).getType().equals("Department")) {
					this.children.add(new HrmOrgDeptOnlySchDTO(entity.getChildren().get(i),key));
				}else if(entity.getChildren().get(i).getType().equals("Division")){
					this.children.add(new HrmOrgDeptOnlySchDTO(entity.getChildren().get(i),key));
				}
			}
			
			
			boolean flag = true;
			if(this.children.isEmpty()) {
				if(this.type.equals("Division") && !this.name.contains(key)) {
					this.id = null;
					this.type = null;
					this.name = null;
					this.code = null;
					this.manager = null;
					this.children = null;
				}
			}else {
				for(int i=0;i<this.children.size(); i++) {
					if(this.children.get(i).getType()!=null){
						flag =false;
					}
				}
				if(flag) {
					this.id = null;
					this.type = null;
					this.name = null;
					this.code = null;
					this.manager = null;
					this.children = null;
				}
			}
				
		}
		
	}
}

