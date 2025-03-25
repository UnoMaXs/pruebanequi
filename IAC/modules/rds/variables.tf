variable "db_name" {
  default     = "franchises"
  type        = string
  description = "Nombre de la base de datos"
}

variable "vpc_id" {
  description = "Id de la vpc"
  type        = string
}

variable "db_username" {
  default     = "admin"
  type        = string
  description = "Nombre de usuario de la base de datos"
}

variable "db_password" {
  type        = string
  description = "Contraseña de la base de datos"
}