output "ecr_repository_url" {
  description = "URL del repositorio de ECR"
  value       = module.ecr.ecr_url
}

output "db_endpoint" {
  description = "Endpoint de la base de datos de RDS"
  value       = module.rds.rds_endpoint
}

output "alb_dns_name" {
  description = "DNS del Application Load Balancer"
  value       = module.alb.alb_dns_name
}

output "ecs_cluster_id" {
  description = "ID del cluster de ECS"
  value       = module.ecs.ecs_cluster_id
}

output "api_invoke_url" {
  description = "URL para realizar las peticiones con el API Gateway"
  value       = module.api_gateway.api_invoke_url
}