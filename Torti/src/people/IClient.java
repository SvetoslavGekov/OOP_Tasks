package people;

import shop.CakeShop;

public interface IClient {
	
	void orderCakes(CakeShop shop);
	
	double getTipPercent();
	
	void setMoney(double money);
	
	double getMoney();
	
	double getStartingMoney();
}
