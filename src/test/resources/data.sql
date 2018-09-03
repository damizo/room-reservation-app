INSERT INTO room_reservation.addresses (id, street, building_number, flat_number, postal_code, city, type)
VALUES ('1', 'Marszalkowska Street', '153', null, '03-553', 'Warsaw', 'HOTEL');

INSERT INTO room_reservation.addresses (id, street, building_number, flat_number, postal_code, city, type)
VALUES ('2', 'Kosciuszki Street', '20', null, '01-158', 'Sopot', 'HOTEL');

INSERT INTO room_reservation.users (id, email, first_name, last_name, phone_number, type)
VALUES ('1', 'andrew@gmail.com', 'Andrew', 'Nowak', '511666444', 'CUSTOMER');

INSERT INTO room_reservation.users (id, email, first_name, last_name, phone_number, type)
VALUES ('20', 'michael@gmail.com', 'Michael', 'Boss', '815861546', 'OWNER');

INSERT INTO room_reservation.hotels (id, name, owner_id, hotel_address_id)
VALUES ('1', 'Ibis', '20', '1');

INSERT INTO room_reservation.hotels (id, name, owner_id, hotel_address_id)
VALUES ('2', 'Rezydent', '20', '2');

INSERT INTO room_reservation.rooms (id, local_number, daily_price, hotel_id)
VALUES ('1', '155', '100', '1');

INSERT INTO room_reservation.rooms (id, local_number, daily_price, hotel_id)
VALUES ('2', '156', '130', '1');

INSERT INTO room_reservation.rooms (id, local_number, daily_price, hotel_id)
VALUES ('3', '170', '260', '1');

INSERT INTO room_reservation.rooms (id, local_number, daily_price, hotel_id)
VALUES ('4', '10', '80', '2');

INSERT INTO room_reservation.rooms (id, local_number, daily_price, hotel_id)
VALUES ('5', '12', '120', '2');

INSERT INTO room_reservation.reservations (id,  period_from, period_to, status, customer_id, room_id, total_price)
    VALUES ('5', '2035-06-04T11:00', '2035-06-16T11:00', 'CONFIRMED', '1', '3', '2860');

INSERT INTO room_reservation.reservations (id, period_from, period_to, status, customer_id, room_id, total_price)
VALUES ('6', '2035-06-04T11:00', '2035-06-06T11:00', 'CANCELED', '1', '3', '520');

INSERT INTO room_reservation.reservations (id, period_from, period_to, status, customer_id, room_id, total_price)
VALUES ('7', '2035-06-07T11:00', '2035-06-10T11:00', 'CONFIRMED', '1', '3', '780');

INSERT INTO room_reservation.reservations (id, period_from, period_to, status, customer_id, room_id, total_price)
VALUES ('9', '2035-06-16T11:00', '2035-06-24T11:00', 'CANCELED', '1', '4', '640')


