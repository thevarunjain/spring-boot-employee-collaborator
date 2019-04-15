INSERT INTO EMPLOYER (id, name) VALUES (7, 'Facebook');
INSERT INTO EMPLOYER (id, name) VALUES (10, 'Google');
INSERT INTO EMPLOYER (id, name) VALUES (20, 'Amazon');

INSERT INTO EMPLOYEE (id, name, email, employer_id) VALUES (101, 'mayur', 'mayur.barge@sjsu.edu', 7);
INSERT INTO EMPLOYEE (id, name, email, employer_id) VALUES (110, 'rajat', 'rajat@sjsu.edu', 10);
INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (102, 'varun', 'varun.jindal@sjsu.edu', 7, 101);
INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (103, 'saket', 'saket@sjsu.edu', 7, 101);
INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (104, 'apurav', 'apurav@sjsu.edu', 7, 102);
INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (105, 'ramesh', 'ramesh@sjsu.edu', 7, 102);

INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (111, 'shubham', 'shubham@sjsu.edu', 10, 110);
INSERT INTO EMPLOYEE (id, name, email, employer_id, manager_id) VALUES (112, 'sheena', 'sheena@sjsu.edu', 10, 110);

-- UPDATE EMPLOYEE SET employer_id=20 WHERE id=110;