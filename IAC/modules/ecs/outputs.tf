output "ecs_cluster_id" {
  description = "Id del cluster ECS que ha sido creado"
  value       = aws_ecs_cluster.franchise_cluster.id
}