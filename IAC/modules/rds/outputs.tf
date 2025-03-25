output "rds_endpoint" {
  description = "Endpoint de la base de datos"
  value       = aws_db_instance.rds_instance.endpoint
}