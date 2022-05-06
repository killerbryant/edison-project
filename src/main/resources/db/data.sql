DELETE FROM UserInfo;
 
INSERT INTO UserInfo (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_GENDER, CUSTOMER_CITY, CUSTOMER_SIGN_TIME) VALUES
  ('A123456', 'Edison', 'M', 'NewTaipeiCity', {ts '2021-09-17 10:47:52'}),
  ('B123456', 'Sandy', 'F', 'Taipei', {ts '2021-09-17 12:47:52'}),
  ('C123456', 'Tom', 'M', 'USA', {ts '2021-09-17 14:47:52'}),
  ('D123456', 'Jack', 'M', 'Japan', {ts '2021-09-17 16:47:52'}),
  ('E123456', 'Mary', 'F', 'Korea', {ts '2021-09-17 18:47:52'});