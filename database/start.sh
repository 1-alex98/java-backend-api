#bin/sh
docker run --mount src="db",target="/var/lib/postgresql/data",type=volume -p "127.0.0.1:5432:5432" --restart=always --name orga-postgres -e POSTGRES_PASSWORD=oragne -d postgres
