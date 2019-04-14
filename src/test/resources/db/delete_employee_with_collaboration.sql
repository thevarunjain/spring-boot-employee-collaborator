INSERT INTO EMPLOYER (id, name) VALUES (1, 'Veritas');
INSERT INTO EMPLOYER (id, name) VALUES (2, 'Facebook');
INSERT INTO EMPLOYEE (id, name, email, employer_id) VALUES (1, 'Sheena', 'shena@gmail.com', 1);
INSERT INTO EMPLOYEE (id, name, email, employer_id) VALUES (2, 'Mayur', 'mayur@gmail.com', 1);
INSERT INTO EMPLOYEE (id, name, email, employer_id) VALUES (3, 'Varun', 'varun@gmail.com', 2);
INSERT INTO COLLABORATION values (1, 2);
INSERT INTO COLLABORATION values (2, 1);
INSERT INTO COLLABORATION values (2, 3);
INSERT INTO COLLABORATION values (3, 2);