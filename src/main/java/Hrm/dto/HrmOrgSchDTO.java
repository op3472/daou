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
@JsonInclude(Include.NON_NULL)
public class HrmOrgSchDTO {
	private Integer id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private List<HrmOrgSchDTO> children;
	
	public HrmOrgSchDTO(final HrmOrgEntity entity,String key) {
		this.id = entity.getId();
		this.type = entity.getType();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.manager = entity.getManager();
		if(!entity.getChildren().isEmpty()) {
			this.children = new ArrayList<HrmOrgSchDTO>();
			
			for(int i=0; i<entity.getChildren().size(); i++) {
				if(entity.getChildren().get(i).getName().contains(key) &&entity.getChildren().get(i).getType().equals("Member")) {
					this.children.add(new HrmOrgSchDTO(entity.getChildren().get(i),key));
				}else if(entity.getChildren().get(i).getType().equals("Division")){
					this.children.add(new HrmOrgSchDTO(entity.getChildren().get(i),key));
				}else if(entity.getChildren().get(i).getType().equals("Department")){
					this.children.add(new HrmOrgSchDTO(entity.getChildren().get(i),key));
				}
			}		
		}
		
		
		boolean flag = true;
		if(this.type.equals("Member")) {
			if(!this.name.contains(key)) {
				this.id = null;
				this.type = null;
				this.name = null;
				this.code = null;
				this.manager = null;
				this.children = null;
			}		
		}else {
			if(this.children==null) {
				this.id = null;
				this.type = null;
				this.name = null;
				this.code = null;
				this.manager = null;
				this.children = null;
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
