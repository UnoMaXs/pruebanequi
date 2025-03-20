variable "aws_region" {
  description = "Región de AWS donde se desplegarán los recursos"
  type        = string
  default     = "us-east-2"
}

variable "vpc_cidr_block" {
  description = "CIDR block de la VPC donde se desplegará la base de datos"
  type        = string
  default     = "10.0.0.0/16"
}

variable "subnet_ids" {
  description = "Lista de IDs de subredes donde se puede desplegar la base de datos"
  type        = list(string)
}

variable "db_username" {
  description = "Nombre de usuario para la base de datos"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Contraseña para la base de datos"
  type        = string
  sensitive   = true
}