package com.example.sop.grpc.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.0)",
    comments = "Source: order_part_validation.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OrderPartValidationServiceGrpc {

  private OrderPartValidationServiceGrpc() {}

  public static final String SERVICE_NAME = "OrderPartValidationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest,
      com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> getValidatePartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidatePart",
      requestType = com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest.class,
      responseType = com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest,
      com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> getValidatePartMethod() {
    io.grpc.MethodDescriptor<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest, com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> getValidatePartMethod;
    if ((getValidatePartMethod = OrderPartValidationServiceGrpc.getValidatePartMethod) == null) {
      synchronized (OrderPartValidationServiceGrpc.class) {
        if ((getValidatePartMethod = OrderPartValidationServiceGrpc.getValidatePartMethod) == null) {
          OrderPartValidationServiceGrpc.getValidatePartMethod = getValidatePartMethod =
              io.grpc.MethodDescriptor.<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest, com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidatePart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OrderPartValidationServiceMethodDescriptorSupplier("ValidatePart"))
              .build();
        }
      }
    }
    return getValidatePartMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OrderPartValidationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceStub>() {
        @Override
        public OrderPartValidationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderPartValidationServiceStub(channel, callOptions);
        }
      };
    return OrderPartValidationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OrderPartValidationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceBlockingStub>() {
        @Override
        public OrderPartValidationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderPartValidationServiceBlockingStub(channel, callOptions);
        }
      };
    return OrderPartValidationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OrderPartValidationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderPartValidationServiceFutureStub>() {
        @Override
        public OrderPartValidationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderPartValidationServiceFutureStub(channel, callOptions);
        }
      };
    return OrderPartValidationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void validatePart(com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest request,
        io.grpc.stub.StreamObserver<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidatePartMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service OrderPartValidationService.
   */
  public static abstract class OrderPartValidationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return OrderPartValidationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service OrderPartValidationService.
   */
  public static final class OrderPartValidationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<OrderPartValidationServiceStub> {
    private OrderPartValidationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OrderPartValidationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderPartValidationServiceStub(channel, callOptions);
    }

    /**
     */
    public void validatePart(com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest request,
        io.grpc.stub.StreamObserver<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidatePartMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service OrderPartValidationService.
   */
  public static final class OrderPartValidationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OrderPartValidationServiceBlockingStub> {
    private OrderPartValidationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OrderPartValidationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderPartValidationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse validatePart(com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidatePartMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service OrderPartValidationService.
   */
  public static final class OrderPartValidationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<OrderPartValidationServiceFutureStub> {
    private OrderPartValidationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OrderPartValidationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderPartValidationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse> validatePart(
        com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidatePartMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VALIDATE_PART = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VALIDATE_PART:
          serviceImpl.validatePart((com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest) request,
              (io.grpc.stub.StreamObserver<com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getValidatePartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationRequest,
              com.example.sop.grpc.generated.OrderPartValidationProto.PartValidationResponse>(
                service, METHODID_VALIDATE_PART)))
        .build();
  }

  private static abstract class OrderPartValidationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OrderPartValidationServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.sop.grpc.generated.OrderPartValidationProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OrderPartValidationService");
    }
  }

  private static final class OrderPartValidationServiceFileDescriptorSupplier
      extends OrderPartValidationServiceBaseDescriptorSupplier {
    OrderPartValidationServiceFileDescriptorSupplier() {}
  }

  private static final class OrderPartValidationServiceMethodDescriptorSupplier
      extends OrderPartValidationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OrderPartValidationServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OrderPartValidationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OrderPartValidationServiceFileDescriptorSupplier())
              .addMethod(getValidatePartMethod())
              .build();
        }
      }
    }
    return result;
  }
}