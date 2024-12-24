package com.example.sop.grpc;

import com.example.sop.grpc.generated.OrderPartValidationProto;
import com.example.sop.grpc.generated.OrderPartValidationServiceGrpc;
import com.example.sopcontracts.exceptions.InsufficientStockException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderPartValidationClient {

    private final OrderPartValidationServiceGrpc.OrderPartValidationServiceBlockingStub stub;

    public OrderPartValidationClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8082)
                .usePlaintext()
                .build();
        this.stub = OrderPartValidationServiceGrpc.newBlockingStub(channel);
    }

    public void validatePart(String partId, int quantity) {
        OrderPartValidationProto.PartValidationRequest request = OrderPartValidationProto.PartValidationRequest.newBuilder()
                .setPartId(partId)
                .setQuantity(quantity)
                .build();

        OrderPartValidationProto.PartValidationResponse response = stub.validatePart(request);

        if (!response.getIsValid()) {
            throw new InsufficientStockException(response.getErrorMessage());
        }

    }
}