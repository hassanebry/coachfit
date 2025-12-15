-- USERS: coaches & clients
CREATE TABLE users (
                       id              BIGSERIAL PRIMARY KEY,
                       full_name       VARCHAR(100)        NOT NULL,
                       email           VARCHAR(255)        NOT NULL UNIQUE,
                       password_hash   VARCHAR(255)        NOT NULL,
                       role            VARCHAR(20)         NOT NULL, -- 'COACH' ou 'CLIENT'
                       created_at      TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
                       updated_at      TIMESTAMPTZ         NOT NULL DEFAULT NOW()
);

-- Lien coach <-> client (un coach peut avoir plusieurs clients)
CREATE TABLE coach_clients (
                               id          BIGSERIAL PRIMARY KEY,
                               coach_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               client_id   BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               UNIQUE (coach_id, client_id)
);

-- EXERCICES (bibliothèque d'exos par coach)
CREATE TABLE exercises (
                           id              BIGSERIAL PRIMARY KEY,
                           coach_id        BIGINT REFERENCES users(id) ON DELETE SET NULL,
                           name            VARCHAR(100)    NOT NULL,
                           primary_muscle  VARCHAR(50)     NOT NULL,
                           equipment       VARCHAR(50),
                           description     TEXT,
                           created_at      TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
                           UNIQUE (coach_id, name)
);

-- PROGRAMMES (perte de poids, prise de masse, etc.)
CREATE TABLE programs (
                          id          BIGSERIAL PRIMARY KEY,
                          coach_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          name        VARCHAR(100)    NOT NULL,
                          goal        VARCHAR(255),
                          level       VARCHAR(50), -- beginner / intermediate / advanced
                          created_at  TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
                          updated_at  TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

-- JOURS DU PROGRAMME (Jour 1, Jour 2, etc.)
CREATE TABLE program_days (
                              id          BIGSERIAL PRIMARY KEY,
                              program_id  BIGINT NOT NULL REFERENCES programs(id) ON DELETE CASCADE,
                              day_index   INT    NOT NULL,     -- 1,2,3...
                              name        VARCHAR(100) NOT NULL,
                              UNIQUE (program_id, day_index)
);

-- EXOS PAR JOUR DE PROGRAMME (template)
CREATE TABLE program_day_exercises (
                                       id                      BIGSERIAL PRIMARY KEY,
                                       program_day_id          BIGINT NOT NULL REFERENCES program_days(id) ON DELETE CASCADE,
                                       exercise_id             BIGINT NOT NULL REFERENCES exercises(id),
                                       order_index             INT    NOT NULL,        -- Ordre dans la séance
                                       sets                    INT    NOT NULL,
                                       reps_min                INT,
                                       reps_max                INT,
                                       target_rpe_min          NUMERIC(3,1),
                                       target_rpe_max          NUMERIC(3,1),
                                       rest_seconds            INT,
                                       notes                   TEXT,
                                       UNIQUE (program_day_id, order_index)
);

-- PROGRAMMES ASSIGNÉS A UN CLIENT
CREATE TABLE assigned_programs (
                                   id              BIGSERIAL PRIMARY KEY,
                                   program_id      BIGINT NOT NULL REFERENCES programs(id) ON DELETE CASCADE,
                                   client_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                   start_date      DATE   NOT NULL,
                                   end_date        DATE,
                                   status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE / COMPLETED / CANCELLED
                                   UNIQUE (program_id, client_id, start_date)
);

-- SÉANCES (instances datées d'un jour de programme)
CREATE TABLE workout_sessions (
                                  id                  BIGSERIAL PRIMARY KEY,
                                  assigned_program_id BIGINT NOT NULL REFERENCES assigned_programs(id) ON DELETE CASCADE,
                                  program_day_id      BIGINT NOT NULL REFERENCES program_days(id),
                                  session_date        DATE   NOT NULL,
                                  status              VARCHAR(20) NOT NULL DEFAULT 'PLANNED', -- PLANNED / DONE / SKIPPED
                                  created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                  UNIQUE (assigned_program_id, program_day_id, session_date)
);

-- SÉRIES LOGGUÉES PAR LE CLIENT
CREATE TABLE logged_sets (
                             id                          BIGSERIAL PRIMARY KEY,
                             session_id                  BIGINT NOT NULL REFERENCES workout_sessions(id) ON DELETE CASCADE,
                             program_day_exercise_id     BIGINT NOT NULL REFERENCES program_day_exercises(id) ON DELETE CASCADE,
                             set_index                   INT    NOT NULL,     -- 1,2,3,4...
                             weight_kg                   NUMERIC(6,2),        -- 9999.99 kg max, on est large
                             reps                        INT,
                             rpe                         NUMERIC(3,1),
                             notes                       TEXT,
                             created_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Index utiles
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_coach_clients_coach ON coach_clients(coach_id);
CREATE INDEX idx_coach_clients_client ON coach_clients(client_id);
CREATE INDEX idx_programs_coach ON programs(coach_id);
CREATE INDEX idx_program_days_program ON program_days(program_id);
CREATE INDEX idx_assigned_programs_client ON assigned_programs(client_id);
CREATE INDEX idx_workout_sessions_assigned ON workout_sessions(assigned_program_id);
CREATE INDEX idx_workout_sessions_date ON workout_sessions(session_date);
CREATE INDEX idx_logged_sets_session ON logged_sets(session_id);