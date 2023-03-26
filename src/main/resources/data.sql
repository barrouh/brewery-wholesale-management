/* brewery */
INSERT INTO brewery (id, name) VALUES (1, 'Abbaye de Leffe');
INSERT INTO brewery (id, name) VALUES (2, 'De Halve Maan Brewery');
INSERT INTO brewery (id, name) VALUES (3, 'Bourgogne des Flandres Brewery');
INSERT INTO brewery (id, name) VALUES (4, 'Cantillon Brewery');

/* beer */
INSERT INTO beer (id, name, alcohol_percentage, price, brewery_id) VALUES (1, 'Leffe Blonde', 6.6, 2.20, 1);
INSERT INTO beer (id, name, alcohol_percentage, price, brewery_id) VALUES (2, 'Ardenne Saison', 5.5, 2.0, 2);
INSERT INTO beer (id, name, alcohol_percentage, price, brewery_id) VALUES (3, 'Avril', 3.5, 1.30, 3);
INSERT INTO beer (id, name, alcohol_percentage, price, brewery_id) VALUES (4, 'St. Feuillien Blonde', 7.5, 3.00, 4);

/* wholesaler */
INSERT INTO wholesaler (id, name) VALUES (1, 'GeneDrinks');
INSERT INTO wholesaler (id, name) VALUES (2, 'BevMo');

/* wholesaler_stock */
INSERT INTO wholesaler_stock (wholesaler_id, beer_id, quantity) VALUES (1, 1, 50);
INSERT INTO wholesaler_stock (wholesaler_id, beer_id, quantity) VALUES (1, 2, 75);
INSERT INTO wholesaler_stock (wholesaler_id, beer_id, quantity) VALUES (2, 3, 23);
INSERT INTO wholesaler_stock (wholesaler_id, beer_id, quantity) VALUES (2, 4, 60);