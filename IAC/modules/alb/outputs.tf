output "alb_dns_name" {
  value       = aws_lb.alb.dns_name
  description = "Dns del balanceador de carga"

}

output "target_group_arn" {
  value       = aws_lb_target_group.target_group.arn
  description = "ARN del target group"
}

output "ecs_task_sg_id" {
  value       = aws_security_group.task-ecs-security-group.id
  description = "Id del security group para tareas"
}