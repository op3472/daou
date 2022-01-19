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
public class HrmSaveDTO {
	private int id;
	private String type;
	private String name;
	private String code;
	private Boolean manager;
	private int parent;
	

	public static HrmOrgEntity toEntity(final HrmSaveDTO dto) {
		return 	HrmOrgEntity.builder()
				.id(dto.getId())
				.type(dto.getType())
				.name(dto.getName())
				.code(dto.getCode())
				.manager(dto.getManager())
				.build();
	}

}
