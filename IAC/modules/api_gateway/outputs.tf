output "api_invoke_url" {
  description = "URL para realizar las peticiones con el API Gateway"
  value       = "https://${aws_api_gateway_rest_api.api.id}.execute-api.${var.region}.amazonaws.com/dev"
}