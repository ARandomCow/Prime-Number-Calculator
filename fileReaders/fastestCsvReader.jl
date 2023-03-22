import Pkg;
Pkg.add("CSV")
Pkg.add("DataFrames")

using CSV, DataFrames

# set the number of rows to read at a time
chunksize = 1000

let 
    prime = 2
    count = 0
    println(prime)

    # read the CSV file in chunks
    # @time CSV.read("00bil_to_10bil.csv", DataFrame, header=false)
    file = CSV.read("00bil_to_10bil.csv", DataFrame, header=false)

    for chunk in Iterators.partition(file, chunksize)
	count += 1

        # convert the chunk to a DataFrame
        df = DataFrame(chunk)

        total = sum(df[!, 1])
        prime += total
	
	if count%10000 == 0
            println(prime)
        end

    end
    println(prime)
end

