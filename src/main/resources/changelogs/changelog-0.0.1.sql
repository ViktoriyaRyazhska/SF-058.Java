--liquibase formatted sql

-- changeset user:TASK_INSERT
INSERT INTO articles (article_headline, caption)
VALUES ('Headline', 'Caption');