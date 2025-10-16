-- SQL Server schema for esun demo (MSSQL)

IF OBJECT_ID(N'dbo.products', N'U') IS NULL
CREATE TABLE dbo.products(
  product_id   VARCHAR(20)   NOT NULL PRIMARY KEY,
  product_name NVARCHAR(100) NOT NULL,
  price        INT           NOT NULL,
  quantity     INT           NOT NULL
);

IF OBJECT_ID(N'dbo.orders', N'U') IS NULL
CREATE TABLE dbo.orders(
  order_id    VARCHAR(30)  NOT NULL PRIMARY KEY,
  member_id   VARCHAR(50)  NOT NULL,
  total_price INT          NOT NULL,
  pay_status  TINYINT      NOT NULL DEFAULT (0),         -- 0/1
  created_at  DATETIME2    NOT NULL DEFAULT (SYSDATETIME())
);

IF OBJECT_ID(N'dbo.order_items', N'U') IS NULL
CREATE TABLE dbo.order_items(
  order_item_sn BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  order_id      VARCHAR(30) NOT NULL,
  product_id    VARCHAR(20) NOT NULL,
  quantity      INT         NOT NULL,
  unit_price    INT         NOT NULL,
  item_price    INT         NOT NULL,
  CONSTRAINT fk_order   FOREIGN KEY (order_id)  REFERENCES dbo.orders(order_id),
  CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES dbo.products(product_id)
);
