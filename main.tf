terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
  required_version = ">= 1.0.0"
}

provider "aws" {
  region = var.aws_region
}

resource "aws_security_group" "rds_sg" {
  name        = "franquicias-api-rds-sg"
  description = "Permitir tráfico para la base de datos de Franquicias API"
  
  ingress {
    description = "MySQL desde VPC"
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr_block]
  }
  
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  tags = {
    Name = "franquicias-api-rds-sg"
  }
}

resource "aws_db_subnet_group" "rds_subnet_group" {
  name       = "franquicias-api-subnet-group"
  subnet_ids = var.subnet_ids
  
  tags = {
    Name = "Franquicias API DB subnet group"
  }
}

resource "aws_db_instance" "franquicias_db" {
  identifier           = "franquicias-api-db"
  allocated_storage    = 20
  storage_type         = "gp2"
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t3.micro"
  db_name              = "franquicias_db"
  username             = var.db_username
  password             = var.db_password
  parameter_group_name = "default.mysql8.0"
  publicly_accessible  = false
  skip_final_snapshot  = true
  
  vpc_security_group_ids = [aws_security_group.rds_sg.id]
  db_subnet_group_name   = aws_db_subnet_group.rds_subnet_group.name
  
  tags = {
    Name = "FranquiciasAPI-Database"
  }
}

output "db_endpoint" {
  value = aws_db_instance.franquicias_db.endpoint
}

output "db_port" {
  value = aws_db_instance.franquicias_db.port
}

output "db_name" {
  value = aws_db_instance.franquicias_db.db_name
}