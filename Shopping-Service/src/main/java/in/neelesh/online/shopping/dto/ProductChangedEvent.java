package in.neelesh.online.shopping.dto;

public record ProductChangedEvent(String productId, String actionType, String storeManagerId) {

}
