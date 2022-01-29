INSERT INTO ROLES (role_description) VALUES
  ('ROLE_CLIENT_FREE'),
  ('ROLE_CLIENT_PREMIUM'),
  ('ROLE_PROFESSIONAL_BASIC'),
  ('ROLE_PROFESSIONAL_BUSINESS');

INSERT INTO MODEL_USER (first_name, last_name, user_name, password, email, birth_date, role_id, enabled) VALUES
  ('Jaime', 'Arana Cardelús', 'jaimearana4', '$2a$10$HMpsTHIjbKfooeGsQvz7tuQT6LIQgeCoxhZpOSK.9V3AKH9T7vBmu', 'jaime.aracarde@gmail.com', DATE '2000-05-24', 1, TRUE),
  ('Paula', 'del Castillo Ventura', 'pauladelcas', '$2a$10$3mCshWfani6gSYFg6UwtiuW5ZAyKrd/8wdr2mNvrozYsBWs88mu1K', 'pauladelcastillo@gmail.com', DATE '2001-03-12', 2, TRUE),
  ('Sylvester', 'Stallone', 'rockybalboa', '$2a$10$NZ9Os7lB.waU0nWUVRdNIOB7PMdOjfl2JeYqVGNviqm1zT4t24Vnm', 'rocky.balboa@gmail.com', DATE '1960-03-03', 3, TRUE),
  ('Arnold', 'Schwarzernegger', 'terminator', '$2a$10$QKk2hnypT4NQTqMx9gvtU.itl0q.gdYtZ0OAQ2mNOTPSHHDO.TMaO', 'terminator@gmail.com', DATE '1963-09-12', 4, TRUE);

  -- JaimeArana2020#, PaulaDelCastillo2020#, RockyBalboa2020#, Terminator2020#

  CREATE TABLE persistent_logins (username varchar(64) not null,
                                series varchar(64) primary key,
                                token varchar(64) not null,
                                last_used timestamp not null)