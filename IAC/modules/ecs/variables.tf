variable "cluster_name" {
  default = "franchises-cluster"
  description = "Nombre del cluster en ecs"
  type        = string
}

variable "subnet_ids" {
  description = "Lista de ids de subredes para las tareas"
  type        = list(string)
}

variable "ecs_task_sg_id" {
  description = "Id del security group para las tareas"
  type        = string
}

variable "repository_url" {
  description = "Url del repositorio en ecr"
  type        = string
}

variable "db_endpoint" {
  description = "Endpoint de la base de datos"
  type        = string
}

variable "db_password" {
  description = "Contraseña de la base de datos"
  type        = string
  sensitive   = true
}

variable "alb_target_group_arn" {
  description = "Arn del target group del balanceador de carga"
  type        = string
}
