### List of relations (obtained via shell command)
```bash
psql -h localhost -p 5432 -U postgres -d library-management-system -c "\dt public.*" -c "\d public.*"
```

```
 Schema |      Name       | Type  |  Owner  
--------+-----------------+-------+----------  
 public | authors         | table | postgres  
 public | book_borrowings | table | postgres  
 public | book_categories | table | postgres  
 public | books           | table | postgres  
 public | categories      | table | postgres  
 public | publishers      | table | postgres  
(6 rows)
```

### Table "public.authors"

```sql
   Column   |          Type          | Collation | Nullable |               Default               
------------+------------------------+-----------+----------+-------------------------------------  
 birth_date | integer                |           |          |  
 id         | integer                |           | not null | nextval('authors_id_seq'::regclass)  
 country    | character varying(255) |           |          |  
 name       | character varying(255) |           | not null |  
Indexes:  
    "authors_pkey" PRIMARY KEY, btree (id)  
Referenced by:  
    TABLE "books" CONSTRAINT "fkfjixh2vym2cvfj3ufxj91jem7" FOREIGN KEY (author_id) REFERENCES authors(id)
```

### Sequence "public.authors_id_seq"

```sql
  Type   | Start | Minimum |  Maximum   | Increment | Cycles? | Cache  
---------+-------+---------+------------+-----------+---------+-------  
 integer |     1 |       1 | 2147483647 |         1 | no      |     1  
Owned by: public.authors.id
```

### Index "public.authors_pkey"

```sql
 Column |  Type   | Key? | Definition  
--------+---------+------+------------  
 id     | integer | yes  | id  
primary key, btree, for table "public.authors"
```

### Table "public.book_borrowings"

```sql
     Column     |          Type          | Collation | Nullable |                   Default                     
----------------+------------------------+-----------+----------+---------------------------------------------  
 book_id        | integer                |           |          |  
 borrowing_date | date                   |           | not null |  
 id             | integer                |           | not null | nextval('book_borrowings_id_seq'::regclass)  
 return_date    | date                   |           |          |  
 borrower_name  | character varying(255) |           | not null |  
Indexes:  
    "book_borrowings_pkey" PRIMARY KEY, btree (id)  
Foreign-key constraints:  
    "fkpo78cf0ph5ya90glc7wv57wdj" FOREIGN KEY (book_id) REFERENCES books(id)
```

### Sequence "public.book_borrowings_id_seq"

```sql
  Type   | Start | Minimum |  Maximum   | Increment | Cycles? | Cache  
---------+-------+---------+------------+-----------+---------+-------  
 integer |     1 |       1 | 2147483647 |         1 | no      |     1  
Owned by: public.book_borrowings.id
```

### Index "public.book_borrowings_pkey"

```sql
 Column |  Type   | Key? | Definition  
--------+---------+------+------------  
 id     | integer | yes  | id  
primary key, btree, for table "public.book_borrowings"
```

### Table "public.book_categories"

```sql
   Column    |  Type   | Collation | Nullable | Default  
-------------+---------+-----------+----------+---------  
 book_id     | integer |           | not null |  
 category_id | integer |           | not null |  
Foreign-key constraints:  
    "fk3k3ahp5vqlgmrr9swqqprmbxy" FOREIGN KEY (book_id) REFERENCES books(id)  
    "fkrg2xlmc92mm2y5b1wmhd2g0y0" FOREIGN KEY (category_id) REFERENCES categories(id)
```

### Table "public.books"

```sql
      Column      |          Type          | Collation | Nullable |              Default                       
------------------+------------------------+-----------+----------+-----------------------------------  
 author_id        | integer                |           |          |  
 id               | integer                |           | not null | nextval('books_id_seq'::regclass)  
 publication_year | integer                |           |          |  
 publisher_id     | integer                |           |          |  
 stock            | integer                |           | not null |  
 name             | character varying(255) |           | not null |  
Indexes:  
    "books_pkey" PRIMARY KEY, btree (id)  
Foreign-key constraints:  
    "fkayy5edfrqnegqj3882nce6qo8" FOREIGN KEY (publisher_id) REFERENCES publishers(id)  
    "fkfjixh2vym2cvfj3ufxj91jem7" FOREIGN KEY (author_id) REFERENCES authors(id)  
Referenced by:  
    TABLE "book_categories" CONSTRAINT "fk3k3ahp5vqlgmrr9swqqprmbxy" FOREIGN KEY (book_id) REFERENCES books(id)  
    TABLE "book_borrowings" CONSTRAINT "fkpo78cf0ph5ya90glc7wv57wdj" FOREIGN KEY (book_id) REFERENCES books(id)
```

### Sequence "public.books_id_seq"

```sql
  Type   | Start | Minimum |  Maximum   | Increment | Cycles? | Cache  
---------+-------+---------+------------+-----------+---------+-------  
 integer |     1 |       1 | 2147483647 |         1 | no      |     1  
Owned by: public.books.id
```

### Index "public.books_pkey"

```sql
 Column |  Type   | Key? | Definition  
--------+---------+------+------------  
 id     | integer | yes  | id  
primary key, btree, for table "public.books"
```

### Table "public.categories"

```sql
   Column    |          Type          | Collation | Nullable |                Default                   
-------------+------------------------+-----------+----------+----------------------------------------  
 id          | integer                |           | not null | nextval('categories_id_seq'::regclass)  
 description | character varying(255) |           |          |  
 name        | character varying(255) |           | not null |  
Indexes:  
    "categories_pkey" PRIMARY KEY, btree (id)  
Referenced by:  
    TABLE "book_categories" CONSTRAINT "fkrg2xlmc92mm2y5b1wmhd2g0y0" FOREIGN KEY (category_id) REFERENCES categories(id)
```

### Sequence "public.categories_id_seq"

```sql
  Type   | Start | Minimum |  Maximum   | Increment | Cycles? | Cache  
---------+-------+---------+------------+-----------+---------+-------  
 integer |     1 |       1 | 2147483647 |         1 | no      |     1  
Owned by: public.categories.id
```

### Index "public.categories_pkey"

```sql
 Column |  Type   | Key? | Definition  
--------+---------+------+------------  
 id     | integer | yes  | id  
primary key, btree, for table "public.categories"
```

### Table "public.publishers"

```sql
       Column       |          Type          | Collation | Nullable |                Default                 
--------------------+------------------------+-----------+----------+----------------------------------------  
 establishment_year | integer                |           |          |  
 id                 | integer                |           | not null | nextval('publishers_id_seq'::regclass)  
 address            | character varying(255) |           |          |  
 name               | character varying(255) |           | not null |  
Indexes:  
    "publishers_pkey" PRIMARY KEY, btree (id)  
Referenced by:  
    TABLE "books" CONSTRAINT "fkayy5edfrqnegqj3882nce6qo8" FOREIGN KEY (publisher_id) REFERENCES publishers(id)
```

### Sequence "public.publishers_id_seq"

```sql
  Type   | Start | Minimum |  Maximum   | Increment | Cycles? | Cache  
---------+-------+---------+------------+-----------+---------+-------  
 integer |     1 |       1 | 2147483647 |         1 | no      |     1  
Owned by: public.publishers.id
```

### Index "public.publishers_pkey"

```sql
 Column |  Type   | Key? | Definition  
--------+---------+------+------------  
 id     | integer | yes  | id  
primary key, btree, for table "public.publishers"
```
