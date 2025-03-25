resource "aws_api_gateway_rest_api" "api" {
  name        = "franchisesApi"
  description = "API Gateway para franquicias"
}

resource "aws_api_gateway_resource" "add_franchise" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  path_part   = "api"
}

resource "aws_api_gateway_resource" "v1" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.add_franchise.id
  path_part   = "v1"
}

#Franchises
resource "aws_api_gateway_resource" "franchise" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.v1.id
  path_part   = "franchise"
}

#Add franchise
resource "aws_api_gateway_resource" "franchise_add" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.franchise.id
  path_part   = "add"
}

resource "aws_api_gateway_method" "franchise_add_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.franchise_add.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "franchise_add_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.franchise_add.id
  http_method             = aws_api_gateway_method.franchise_add_method.http_method
  integration_http_method = "POST"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/franchise/add"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Update franchise
resource "aws_api_gateway_resource" "franchise_update" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.franchise.id
  path_part   = "update"
}

resource "aws_api_gateway_method" "franchise_update_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.franchise_update.id
  http_method   = "PATCH"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "franchise_update_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.franchise_update.id
  http_method             = aws_api_gateway_method.franchise_update_method.http_method
  integration_http_method = "PATCH"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/franchise/update"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Branch
resource "aws_api_gateway_resource" "Branch" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.v1.id
  path_part   = "branch"
}

#Add branch
resource "aws_api_gateway_resource" "branch_add" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.Branch.id
  path_part   = "add"
}

resource "aws_api_gateway_method" "branch_add_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.branch_add.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "branch_add_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.branch_add.id
  http_method             = aws_api_gateway_method.branch_add_method.http_method
  integration_http_method = "POST"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/branch/add"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Update branch
resource "aws_api_gateway_resource" "branch_update" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.Branch.id
  path_part   = "update"
}

resource "aws_api_gateway_method" "branch_update_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.branch_update.id
  http_method   = "PATCH"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "branch_update_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.branch_update.id
  http_method             = aws_api_gateway_method.branch_update_method.http_method
  integration_http_method = "PATCH"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/branch/update"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Product
resource "aws_api_gateway_resource" "product" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.v1.id
  path_part   = "product"
}

resource "aws_api_gateway_resource" "product_add" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.product.id
  path_part   = "add"
}

resource "aws_api_gateway_method" "product_add_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product_add.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_add_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product_add.id
  http_method             = aws_api_gateway_method.product_add_method.http_method
  integration_http_method = "POST"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/product/add"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#update product
resource "aws_api_gateway_resource" "product_update" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.product.id
  path_part   = "update"
}

resource "aws_api_gateway_method" "product_update_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product_update.id
  http_method   = "PATCH"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_update_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product_update.id
  http_method             = aws_api_gateway_method.product_update_method.http_method
  integration_http_method = "PATCH"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/product/update"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#delete product
resource "aws_api_gateway_resource" "product_delete" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.product.id
  path_part   = "delete"
}

resource "aws_api_gateway_method" "product_delete_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product_delete.id
  http_method   = "DELETE"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_delete_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product_delete.id
  http_method             = aws_api_gateway_method.product_delete_method.http_method
  integration_http_method = "DELETE"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/product/delete"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#getStock products
resource "aws_api_gateway_resource" "product_get_stock" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.product.id
  path_part   = "get"
}

resource "aws_api_gateway_method" "product_get_stock_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product_get_stock.id
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_get_stock_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product_get_stock.id
  http_method             = aws_api_gateway_method.product_get_stock_method.http_method
  integration_http_method = "GET"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/product/get"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Update stock
resource "aws_api_gateway_resource" "product_update_stock" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.product_update.id
  path_part   = "stock"
}

resource "aws_api_gateway_method" "product_update_stock_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product_update_stock.id
  http_method   = "PATCH"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_update_stock_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product_update_stock.id
  http_method             = aws_api_gateway_method.product_update_stock_method.http_method
  integration_http_method = "PATCH"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/api/v1/product/update/stock"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#Despliegue api gateway
resource "aws_api_gateway_deployment" "api_deployment" {
  depends_on = [
    aws_api_gateway_integration.franchise_update_integration,
    aws_api_gateway_integration.franchise_add_integration,
    aws_api_gateway_integration.branch_add_integration,
    aws_api_gateway_integration.branch_update_integration,
    aws_api_gateway_integration.product_add_integration,
    aws_api_gateway_integration.product_update_integration,
    aws_api_gateway_integration.product_delete_integration,
    aws_api_gateway_integration.product_get_stock_integration,
    aws_api_gateway_integration.product_update_stock_integration
  ]
  rest_api_id = aws_api_gateway_rest_api.api.id
  stage_name  = "dev"
}