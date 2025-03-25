variable "vpc_id" {
  description = "Id de la vpc"
  type        = string
}

variable "subnet_ids" {
  type = list(string)
}