	create table pizza_size(
				id integer not null, 
				s_status integer not null,  -- 0 = inactive, 1 = active
				size_name varchar(30) not null,
				primary key (id),
				unique (size_name));

	create table pizza_orders(
				id integer not null, 
				room_number integer not null, 
				size_id integer not null, 
				day integer not null, 
				status integer not null,
				primary key(id),
				constraint po_size_id foreign key (size_id) references pizza_size(id));

						
	create table toppings(
				id integer not null, 
				t_status integer not null,  -- 0 = inactive, 1 = active
				topping_name varchar(30) not null,
				primary key(id),
				unique (topping_name));

	create table order_topping (
				order_id integer not null, 
				topping_id integer not null,
				primary key (order_id, topping_id),
				constraint ot_order_id foreign key (order_id) references pizza_orders (id),
				constraint ot_topping_id foreign key (topping_id) references toppings(id));
		
	create table pizza_sys_tab (
				id integer not null,
				current_day integer not null, 
				last_report integer not null,
				primary key(id));
		
	insert into pizza_sys_tab values (1, 1, 1);
		
	--for generated ids specific to the pizza project
	--pizza_id_gen has one row for each table that needs ids, i.e. each entity table
	--gen_val start at 0, so first generated id for each entity is 1
	CREATE TABLE PIZZA_ID_GEN (GEN_NAME VARCHAR(50) NOT NULL, GEN_VAL INTEGER, PRIMARY KEY (GEN_NAME));
	INSERT INTO PIZZA_ID_GEN (GEN_NAME, GEN_VAL) values ('Ordno_Gen', 0);
	INSERT INTO PIZZA_ID_GEN (GEN_NAME, GEN_VAL) values ('SizeId_Gen', 0);
	INSERT INTO PIZZA_ID_GEN (GEN_NAME, GEN_VAL) values ('ToppingId_Gen', 0);


