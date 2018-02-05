package rodionkonioshko.com.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Ingredient base class
 * will store all of the attributes that the ingredient has to offer within the json file
 */
public class Ingredient implements Parcelable
{
    //quantity of that ingredient
    @SerializedName("quantity")
    private double mQuantity;
    //the way it is being measured
    @SerializedName("measure")
    private String mMeasure;
    //the ingredient name
    @SerializedName("ingredient")
    private String mIngredient;


    protected Ingredient(Parcel in)
    {
        mQuantity = in.readDouble();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>()
    {
        @Override
        public Ingredient createFromParcel(Parcel in)
        {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size)
        {
            return new Ingredient[size];
        }
    };

    public double getmQuantity()
    {
        return mQuantity;
    }

    public String getmMeasure()
    {
        return mMeasure;
    }

    public String getmIngredient()
    {
        return mIngredient;
    }

    @Override
    public String toString()
    {
        return "Ingredients{" +
                "mQuantity=" + mQuantity +
                ", mMeasure='" + mMeasure + '\'' +
                ", mIngredient='" + mIngredient + '\'' +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
