insert into PUBLICATION(publication, id, publisher, title, year, language, author,time_publication, isbn, pages) values
('book', 1, 'Jan', 'Kowalski', 2457, null, 'MARCIN', null, 24141, 2122),
('book', 2, 'Maciej', 'Zalewski', 2456, null,'MARCIN', null, 241412, 211),
('book', 3, 'Aneta', 'Korczyńska', 6749, null, 'MARCIN',null,  241411, 213),
('book', 4, 'Wojciech', 'Sokolik', 7245, null, 'MARCIN', null, 241416, 212),
('magazine', 5, 'Jan', 'Kowalski', 2457, 'POLSKI', null, '2007-12-03', null, null),
('magazine', 6, 'Maciej', 'Zalewski', 2456, 'POLSKI', null, '2007-12-03', null, null),
('magazine', 7, 'Aneta', 'Korczyńska', 6749, 'POLSKI',null,  '2007-12-03', null, null),
('magazine', 8, 'Wojciech', 'Sokolik', 7245, 'POLSKI', null, '2007-12-03', null, null);



insert into USER(id, FIRST_NAME, LAST_NAME, pesel) values ( 1, 'Marcin', 'Klimowicz', 432423423 )