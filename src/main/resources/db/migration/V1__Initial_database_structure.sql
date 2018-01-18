CREATE CACHED TABLE university(
    id int auto_increment primary key,
    name varchar(32)
);

CREATE CACHED TABLE specialization(
    id int auto_increment primary key,
    name varchar(32),
    university_id int not null,
    foreign key (university_id) references university(id)
);

CREATE CACHED TABLE candidate(
    id int auto_increment primary key,
    first_name varchar(64),
    last_name varchar(64),
    email varchar(64),
    phone varchar(20),
    study_year int,
    specialization_id int not null,
    foreign key (specialization_id) references specialization(id)
);
