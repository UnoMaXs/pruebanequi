variable "alb_dns_name" {
  description = "DNS del balanceador de carga para el manejo de las integraciones con el API GATEWAY"
  type        = string
}

variable "region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1"
}