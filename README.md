# cash-book
A Cash book app to record transactions between N number of entities. Built using Spring Boot, PostgreSQL and ReactJS. 

# Features
1. Uses JWT authentication to login users.
2. Uses bcrypt encoder to hash and store passwords.
3. Role based user management system like allowing granular control over transaction management.
  i. USER - Read only transactions
  ii. ADMIN - Above and move money around i.e execute transactions.
  iii. SUPER USER - Above and Add new entities to participate.
4. Frontend is built using React. Code in src/main/frontend
5. Single gradle file to compile and run Backend as well as Frontend. (Check build.gradle)

# Test Account

URL: http://hmdrf.herokuapp.com/

username - dummy

password - dummy


above account has role 'USER' so can only view transactions.
Ping me on [LinkedIn][https://linkedin.com/in/omkarlubal] to access SUPER USER or ADMIN


