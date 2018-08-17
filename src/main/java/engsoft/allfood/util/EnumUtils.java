package engsoft.allfood.util;

import engsoft.allfood.enums.DeliveryType;
import engsoft.allfood.enums.PaymentType;

public class EnumUtils {
	
	public static DeliveryType getDeliveryType(int type) {
		switch(type) {
		case 0:
			return DeliveryType.AT_HOME;
		default:
			return DeliveryType.AT_MARKET;
		}
	}
	
	public static PaymentType getPaymentType(int type) {
		switch(type) {
		case 0:
			return PaymentType.CARD;
		case 1:
			return PaymentType.TICKET;
		default:
			return PaymentType.MONEY;
		}
	}

}
