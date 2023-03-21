import Pkg;
Pkg.add("CSV")
Pkg.add("DataFrames")

intArray = Array{Int16}(undef, 5, 1)
fill!(intArray, 7)

print(intArray)

intList = (1, 2, 3, 4, 5, 6)
for i in intList
    println(i^2)
end

# using CSV, DataFrames

# # read the CSV file into a DataFrame
# df = CSV.read("0mil_to_1mil.csv", DataFrame)
# println("CSV read")
# # print the contents of the DataFrame



using CSV, DataFrames

# set the number of rows to read at a time
chunksize = 1000

let 
    prime = 2
    count = 0
    println(prime)
    # read the CSV file in chunks
    @time CSV.read("00bil_to_10bil.csv", DataFrame, header=false)
    file = CSV.read("00bil_to_10bil.csv", DataFrame, header=false)
    for chunk in Iterators.partition(file, chunksize)
        # convert the chunk to a DataFrame
        df = DataFrame(chunk)

        # process the chunk as needed
        total = sum(df[!, 1])
        prime += total
        # println("The sum of this chunk is $total")
    end
    println(prime)
    # close the file
end

