# HW08-Online-Shop
Maktab Online Shop assignment with basic features.

Program Flow:
1-Manager Adds Category

2-Manager Adds Product

3-Customer Adds Product to Shopping Cart

4-Customer can check out shopping cart and make an order

5-Customer can finalize an order if balance is enough

------------------------------------------------------------
Orders in database:
------------------------------------------------------------
Each order contains only ID,Customer,Status and Date and its items are found with OrderDetail Class that gets initialized with order_to_product relation table.

order_to_product gets initialized by as many rows as an order has products.

After initialization,an order or its details can't be compromised.

-------------------------------------------------------------