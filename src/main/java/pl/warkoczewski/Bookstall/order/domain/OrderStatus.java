package pl.warkoczewski.Bookstall.order.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;


public enum OrderStatus {
    NEW {
        @Override
        public OrderStatus updateOrderStatus(OrderStatus orderStatus) {
            return switch (orderStatus){
                case PAID -> PAID;
                case CANCELED -> CANCELED;
                case ABANDONED -> ABANDONED;
                default -> super.updateOrderStatus(orderStatus);
            };
        }
    }, PAID {
        @Override
        public OrderStatus updateOrderStatus(OrderStatus orderStatus) {
            if (orderStatus == SHIPPED){
                return SHIPPED;
            }
            return super.updateOrderStatus(orderStatus);
        }
    }, CANCELED {

    }

    , ABANDONED, SHIPPED;

    public static Optional<OrderStatus> parseString(String value){
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> StringUtils.equalsIgnoreCase(orderStatus.name(), value))
                .findFirst();
    }

    public OrderStatus updateOrderStatus(OrderStatus orderStatus){
        throw new IllegalArgumentException("Unable to mark " + this.name() + " order as " + orderStatus.name());
    }
}
