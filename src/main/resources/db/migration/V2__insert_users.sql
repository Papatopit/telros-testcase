ALTER TABLE public.users
ADD CONSTRAINT uk_users_phone UNIQUE (phone);

INSERT INTO public.users(
    user_id, login, password, email, firstname, lastname, surname, phone, birthday, photo)
VALUES
    (1, 'ADMIN', '$2a$12$lEx0Z5ow6xf8FfYq0G3EI.Skf7iZS1EDQof9ZobkMrr3t/F1RegTi',
     'pablito@mail.ru', 'Pablo', 'Escobar', 'Юрич', '89923338768', '2002-06-07', null),

    (2, 'USER', '$2a$12$PwNeOy/hFQSvCRDv.b7jVO0UU3IrkbTatQKY0hHrcC0bkt3DUOS0W',
     'armen@mail.ru', 'Armen', 'Gasporyan', 'Mahitych', '89923338769', '1999-06-06', null);

INSERT INTO public.user_roles
VALUES (1, 1), (2, 2);

-- токен для тестирования аккаунта admin
INSERT INTO public.tokens (token, user_id)
VALUES
    ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhd' ||
     'CI6MTY4NDMwNDYzNywiZXhwIjoxNjg1MTY4NjM3fQ.ySOvttFX586SZtADriC-d5G0l3d2zbUQy-p44QKPMy4', 1);