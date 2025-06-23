package in.neelesh.online.shopping.dto;

import java.util.List;

public record ProductResponseDto(String id, String name, String description, Double price, List<String> images,
		Integer quantity) {

}
